package cn.fetosoft.chat.connect.chat;

import lombok.Getter;
import lombok.Setter;

/**
 * 语音信息
 * @author guobingbing
 * @create 2020/12/22 18:30
 */
@Setter
@Getter
public class ChatAudio extends FileMetadata {

	private int duration;

	private long size;
}
