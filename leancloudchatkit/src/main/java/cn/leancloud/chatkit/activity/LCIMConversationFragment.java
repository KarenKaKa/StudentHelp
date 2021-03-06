package cn.leancloud.chatkit.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.leancloud.AVException;
import cn.leancloud.callback.AVCallback;
import cn.leancloud.chatkit.R;
import cn.leancloud.chatkit.adapter.LCIMChatAdapter;
import cn.leancloud.chatkit.cache.LCIMLocalCacheUtils;
import cn.leancloud.chatkit.event.LCIMConversationReadStatusEvent;
import cn.leancloud.chatkit.event.LCIMIMTypeMessageEvent;
import cn.leancloud.chatkit.event.LCIMInputBottomBarEvent;
import cn.leancloud.chatkit.event.LCIMInputBottomBarRecordEvent;
import cn.leancloud.chatkit.event.LCIMInputBottomBarTextEvent;
import cn.leancloud.chatkit.event.LCIMMessageResendEvent;
import cn.leancloud.chatkit.event.LCIMMessageUpdateEvent;
import cn.leancloud.chatkit.event.LCIMMessageUpdatedEvent;
import cn.leancloud.chatkit.event.LCIMOfflineMessageCountChangeEvent;
import cn.leancloud.chatkit.utils.LCIMAudioHelper;
import cn.leancloud.chatkit.utils.LCIMConstants;
import cn.leancloud.chatkit.utils.LCIMConversationUtils;
import cn.leancloud.chatkit.utils.LCIMLogUtils;
import cn.leancloud.chatkit.utils.LCIMNotificationUtils;
import cn.leancloud.chatkit.utils.LCIMPathUtils;
import cn.leancloud.chatkit.view.LCIMInputBottomBar;
import cn.leancloud.im.v2.AVIMConversation;
import cn.leancloud.im.v2.AVIMException;
import cn.leancloud.im.v2.AVIMMessage;
import cn.leancloud.im.v2.AVIMMessageOption;
import cn.leancloud.im.v2.callback.AVIMConversationCallback;
import cn.leancloud.im.v2.callback.AVIMMessageRecalledCallback;
import cn.leancloud.im.v2.callback.AVIMMessageUpdatedCallback;
import cn.leancloud.im.v2.callback.AVIMMessagesQueryCallback;
import cn.leancloud.im.v2.messages.AVIMAudioMessage;
import cn.leancloud.im.v2.messages.AVIMImageMessage;
import cn.leancloud.im.v2.messages.AVIMRecalledMessage;
import cn.leancloud.im.v2.messages.AVIMTextMessage;
import de.greenrobot.event.EventBus;

/**
 * Created by wli on 15/8/27.
 * ?????????????????????????????? Fragment ???????????????????????? setConversation ?????? Conversation ??????
 */
public class LCIMConversationFragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    protected AVIMConversation imConversation;

    /**
     * recyclerView ????????? Adapter
     */
    protected LCIMChatAdapter itemAdapter;

    protected RecyclerView recyclerView;
    protected LinearLayoutManager layoutManager;

    /**
     * ????????????????????? SwipeRefreshLayout
     */
    protected SwipeRefreshLayout refreshLayout;

    /**
     * ??????????????????
     */
    protected LCIMInputBottomBar inputBottomBar;

    // ??????????????????????????????
    protected String localCameraPath;
    private List<AVIMMessage> messageLists;

    public void setConversation(final AVIMConversation conversation) {
        imConversation = conversation;
        refreshLayout.setEnabled(true);
        inputBottomBar.setTag(imConversation.getConversationId());
        fetchMessages();
        imConversation.read();
        LCIMNotificationUtils.addTag(conversation.getConversationId());
        if (!conversation.isTransient()) {
            if (conversation.getMembers().size() == 0) {
                conversation.fetchInfoInBackground(new AVIMConversationCallback() {
                    @Override
                    public void done(AVIMException e) {
                        if (null != e) {
                            LCIMLogUtils.logException(e);
                            Toast.makeText(getContext(), "encounter network error, please try later.", Toast.LENGTH_SHORT);
                        }
                        itemAdapter.showUserName(conversation.getMembers().size() > 2);
                    }
                });
            } else {
                itemAdapter.showUserName(conversation.getMembers().size() > 2);
            }
        } else {
            itemAdapter.showUserName(true);
        }
    }

    /**
     * ??????????????????????????? conversation ?????????????????????
     */
    private void fetchMessages() {
        imConversation.queryMessages(new AVIMMessagesQueryCallback() {
            @Override
            public void done(List<AVIMMessage> messageList, AVIMException e) {
                if (filterException(e)) {
                    messageLists = messageList;
                    itemAdapter.setMessageList(messageList);
                    recyclerView.setAdapter(itemAdapter);
                    itemAdapter.setDeliveredAndReadMark(imConversation.getLastDeliveredAt(),
                            imConversation.getLastReadAt());
                    itemAdapter.notifyDataSetChanged();
                    scrollToBottom();
                    clearUnreadConut();
                }
            }
        });
    }

    /**
     * ?????? recyclerView ?????????
     */
    private void scrollToBottom() {
        layoutManager.scrollToPositionWithOffset(itemAdapter.getItemCount() - 1, 0);
    }

    private void clearUnreadConut() {
        if (imConversation.getUnreadMessagesCount() > 0) {
            imConversation.read();
        }
    }

    public void exportFile() {
        if (messageLists != null && !messageLists.isEmpty()) {
            LCIMConversationUtils.getConversationName(imConversation, new AVCallback<String>() {
                @Override
                protected void internalDone0(String s, AVException e) {
                    if (null != e) {
                        LCIMLogUtils.logException(e);
                        LCIMLocalCacheUtils.writeListIntoSDcard(requireActivity(), imConversation.getCreator(), messageLists);
                    } else {

                        LCIMLocalCacheUtils.writeListIntoSDcard(requireActivity(), s, messageLists);
                    }
                }
            });

        }
    }

    /**
     * ??????????????????????????????????????? AVIMTextMessage ????????????
     * ???????????????????????????????????????????????????????????????????????????????????????????????? tag ??????
     */
    public void onEvent(LCIMInputBottomBarTextEvent textEvent) {
        if (null != imConversation && null != textEvent) {
            if (!TextUtils.isEmpty(textEvent.sendContent) && imConversation.getConversationId().equals(textEvent.tag)) {
                sendText(textEvent.sendContent);
            }
        }
    }

    /**
     * ??????????????????
     *
     * @param content
     */
    protected void sendText(String content) {
        AVIMTextMessage message = new AVIMTextMessage();
        message.setText(content);
        sendMessage(message);
    }

    public void sendMessage(AVIMMessage message) {
        sendMessage(message, true);
    }

    /**
     * ????????????
     *
     * @param message
     */
    public void sendMessage(AVIMMessage message, boolean addToList) {
        AVIMMessageOption option = new AVIMMessageOption();
        if (message instanceof AVIMTextMessage) {
            AVIMTextMessage textMessage = (AVIMTextMessage) message;
            if (textMessage.getText().startsWith("tr:")) {
                option.setTransient(true);
            } else {
                option.setReceipt(true);
            }
        } else {
            option.setReceipt(true);
        }
        imConversation.sendMessage(message, option, new AVIMConversationCallback() {
            @Override
            public void done(AVIMException e) {
                if (addToList) {
                    itemAdapter.addMessage(message);
                }
                itemAdapter.notifyDataSetChanged();
                scrollToBottom();
                if (null != e) {
                    LCIMLogUtils.logException(e);
                }
            }
        });
    }

    /**
     * ???????????????????????????
     * ?????????????????????????????????????????? conversation id ??????
     */
    public void onEvent(LCIMIMTypeMessageEvent messageEvent) {
        if (null != imConversation && null != messageEvent &&
                imConversation.getConversationId().equals(messageEvent.conversation.getConversationId())) {
            System.out.println("currentConv unreadCount=" + imConversation.getUnreadMessagesCount());
            if (imConversation.getUnreadMessagesCount() > 0) {
                paddingNewMessage(imConversation);
            } else {
                itemAdapter.addMessage(messageEvent.message);
                itemAdapter.notifyDataSetChanged();
                scrollToBottom();
            }
        }
    }

    private void paddingNewMessage(AVIMConversation currentConversation) {
        if (null == currentConversation || currentConversation.getUnreadMessagesCount() < 1) {
            return;
        }
        final int queryLimit = currentConversation.getUnreadMessagesCount() > 100 ? 100 : currentConversation.getUnreadMessagesCount();
        currentConversation.queryMessages(queryLimit, new AVIMMessagesQueryCallback() {
            @Override
            public void done(List<AVIMMessage> list, AVIMException e) {
                if (null != e) {
                    return;
                }
                for (AVIMMessage m : list) {
                    itemAdapter.addMessage(m);
                }
                itemAdapter.notifyDataSetChanged();
                scrollToBottom();
                clearUnreadConut();
            }
        });
    }

    /**
     * ???????????????????????????????????????
     */
    public void onEvent(LCIMMessageResendEvent resendEvent) {
        if (null != imConversation && null != resendEvent &&
                null != resendEvent.message && imConversation.getConversationId().equals(resendEvent.message.getConversationId())) {
            if (AVIMMessage.AVIMMessageStatus.AVIMMessageStatusFailed == resendEvent.message.getMessageStatus()
                    && imConversation.getConversationId().equals(resendEvent.message.getConversationId())) {
                sendMessage(resendEvent.message, false);
            }
        }
    }

    /**
     * ????????????????????????????????????
     *
     * @param event
     */
    public void onEvent(LCIMInputBottomBarEvent event) {
        if (null != imConversation && null != event && imConversation.getConversationId().equals(event.tag)) {
            switch (event.eventAction) {
                case LCIMInputBottomBarEvent.INPUTBOTTOMBAR_IMAGE_ACTION:
                    dispatchPickPictureIntent();
                    break;
                case LCIMInputBottomBarEvent.INPUTBOTTOMBAR_CAMERA_ACTION:
                    dispatchTakePictureIntent();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * ?????? Intent ???????????????????????????
     */
    private void dispatchPickPictureIntent() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, null);
        photoPickerIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(photoPickerIntent, REQUEST_IMAGE_PICK);
    }

    /**
     * ?????? Intent ???????????????????????????
     */
    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            localCameraPath = LCIMPathUtils.getPicturePathByCurrentTime(getContext());
            Uri imageUri = Uri.fromFile(new File(localCameraPath));
            takePictureIntent.putExtra("return-data", false);
            takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);
        } else {
            localCameraPath = Environment.getExternalStorageDirectory() + "/images/" + System.currentTimeMillis() + ".jpg";
            File photoFile = new File(localCameraPath);

            Uri photoURI = FileProvider.getUriForFile(this.getContext(),
                    this.getContext().getPackageName() + ".provider", photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                    photoURI);
        }
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /**
     * ??????????????????
     *
     * @param recordEvent
     */
    public void onEvent(LCIMInputBottomBarRecordEvent recordEvent) {
        if (null != imConversation && null != recordEvent &&
                !TextUtils.isEmpty(recordEvent.audioPath) &&
                imConversation.getConversationId().equals(recordEvent.tag)) {
            if (recordEvent.audioDuration > 0)
                sendAudio(recordEvent.audioPath);
        }
    }

    /**
     * ??????????????????
     *
     * @param audioPath
     */
    protected void sendAudio(String audioPath) {
        try {
            AVIMAudioMessage audioMessage = new AVIMAudioMessage(audioPath);
            sendMessage(audioMessage);
        } catch (IOException e) {
            LCIMLogUtils.logException(e);
        }
    }

    /**
     * ?????????????????????????????????
     *
     * @param readEvent
     */
    public void onEvent(LCIMConversationReadStatusEvent readEvent) {
        if (null != imConversation && null != readEvent &&
                imConversation.getConversationId().equals(readEvent.conversationId)) {
            itemAdapter.setDeliveredAndReadMark(imConversation.getLastDeliveredAt(),
                    imConversation.getLastReadAt());
            itemAdapter.notifyDataSetChanged();
        }
    }

    public void onEvent(final LCIMMessageUpdateEvent event) {
        if (null != imConversation && null != event &&
                null != event.message && imConversation.getConversationId().equals(event.message.getConversationId())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("??????").setItems(new String[]{"??????", "??????????????????"}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (0 == which) {
                        recallMessage(event.message);
                    } else if (1 == which) {
                        showUpdateMessageDialog(event.message);
                    }
                }
            });
            builder.create().show();
        }
    }

    private void recallMessage(AVIMMessage message) {
        imConversation.recallMessage(message, new AVIMMessageRecalledCallback() {
            @Override
            public void done(AVIMRecalledMessage recalledMessage, AVException e) {
                if (null == e) {
                    itemAdapter.updateMessage(recalledMessage);
                } else {
                    Toast.makeText(getActivity(), "????????????", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showUpdateMessageDialog(final AVIMMessage message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final EditText editText = new EditText(getActivity());
        builder.setView(editText);
        builder.setTitle("??????????????????");
        builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String content = editText.getText().toString();
                updateMessage(message, content);
            }
        });
        builder.show();
    }

    private void updateMessage(AVIMMessage message, String newContent) {
        AVIMTextMessage textMessage = new AVIMTextMessage();
        textMessage.setText(newContent);
        imConversation.updateMessage(message, textMessage, new AVIMMessageUpdatedCallback() {
            @Override
            public void done(AVIMMessage message, AVException e) {
                if (null == e) {
                    itemAdapter.updateMessage(message);
                } else {
                    Toast.makeText(getActivity(), "????????????", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onEvent(final LCIMMessageUpdatedEvent event) {
        if (null != imConversation && null != event &&
                null != event.message && imConversation.getConversationId().equals(event.message.getConversationId())) {
            itemAdapter.updateMessage(event.message);
        }
    }

    public void onEvent(final LCIMOfflineMessageCountChangeEvent event) {
        if (null == event || null == event.conversation || null == event.conversation) {
            return;
        }
        if (!imConversation.getConversationId().equals(event.conversation.getConversationId())) {
            return;
        }
        if (event.conversation.getUnreadMessagesCount() < 1) {
            return;
        }
        paddingNewMessage(event.conversation);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("requestCode=" + requestCode + ", resultCode=" + resultCode);
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    sendImage(localCameraPath);
                    break;
                case REQUEST_IMAGE_PICK:
                    sendImage(getRealPathFromURI(getActivity(), data.getData()));
                    break;
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lcim_conversation_fragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_chat_rv_chat);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_chat_srl_pullrefresh);
        refreshLayout.setEnabled(false);
        inputBottomBar = (LCIMInputBottomBar) view.findViewById(R.id.fragment_chat_inputbottombar);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        itemAdapter = getAdpter();
        itemAdapter.resetRecycledViewPoolSize(recyclerView);
        recyclerView.setAdapter(itemAdapter);

        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                AVIMMessage message = itemAdapter.getFirstMessage();
                if (null == message) {
                    refreshLayout.setRefreshing(false);
                } else {
                    imConversation.queryMessages(message.getMessageId(), message.getTimestamp(), 20, new AVIMMessagesQueryCallback() {
                        @Override
                        public void done(List<AVIMMessage> list, AVIMException e) {
                            refreshLayout.setRefreshing(false);
                            if (filterException(e)) {
                                if (null != list && list.size() > 0) {
                                    itemAdapter.addMessageList(list);
                                    itemAdapter.setDeliveredAndReadMark(imConversation.getLastDeliveredAt(),
                                            imConversation.getLastReadAt());
                                    itemAdapter.notifyDataSetChanged();
                                    layoutManager.scrollToPositionWithOffset(list.size() - 1, 0);
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != imConversation) {
            LCIMNotificationUtils.addTag(imConversation.getConversationId());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        LCIMAudioHelper.getInstance().stopPlayer();
        if (null != imConversation) {
            LCIMNotificationUtils.removeTag(imConversation.getConversationId());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.conv_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_conv_setting) {
            Intent intent = new Intent(getActivity(), LCIMConversationDetailActivity.class);
            intent.putExtra(LCIMConstants.CONVERSATION_ID, imConversation.getConversationId());
            getActivity().startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean filterException(Exception e) {
        if (null != e) {
            LCIMLogUtils.logException(e);
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return (null == e);
    }

    protected LCIMChatAdapter getAdpter() {
        return new LCIMChatAdapter();
    }

    /**
     * ??????????????????
     * TODO ????????????????????????????????????
     *
     * @param imagePath
     */
    protected void sendImage(String imagePath) {
        try {
            sendMessage(new AVIMImageMessage(imagePath));
        } catch (IOException e) {
            LCIMLogUtils.logException(e);
        }
    }

    /**
     * ?????? Uri ???????????????????????????
     *
     * @param context
     * @param contentUri
     * @return
     */
    private String getRealPathFromURI(Context context, Uri contentUri) {
        if (contentUri.getScheme().equals("file")) {
            return contentUri.getEncodedPath();
        } else {
            Cursor cursor = null;
            try {
                String[] proj = {MediaStore.Images.Media.DATA};
                cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
                if (null != cursor) {
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    return cursor.getString(column_index);
                } else {
                    return "";
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
    }
}
