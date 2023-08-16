package com.twg0.upgradecapstone.message.dto;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.timcook.capstone.device.domain.Device;
import com.timcook.capstone.message.domain.MessageFormat;
import com.timcook.capstone.message.domain.ReplyMessage;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReplyMessageCreateRequest implements MessageCreateRequsetInterface{
	
	@NotNull
	private Device device;
	@NotNull
	private String title;
	@NotNull
	private int kindOfReply;
	@NotNull
	private String fileId;
	
	@Builder
	public ReplyMessageCreateRequest(Device device, String title, int kindOfReply, String fileId) {
		this.device = device;
		this.title = title;
		this.kindOfReply = kindOfReply;
		this.fileId = fileId;
	}
	
	public ReplyMessageCreateRequest(List<String> payload) {
		this.title = payload.get(MessageFormat.TITLE.getIndex());
		this.kindOfReply = Integer.parseInt(payload.get(MessageFormat.REPLY_KIND.getIndex()));
		this.fileId = payload.get(MessageFormat.FILE_ID.getIndex());
	}

	public ReplyMessage toEntity() {
		return ReplyMessage.builder()
							.device(device)
							.title(title)
							.reply_sort(kindOfReply)
							.fileId(fileId)
							.build();
	}
}
