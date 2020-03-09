package beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="comment_reply")
public class CommentReply {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="reply_id")
	private int id;
	@Column(name="comment_id")
	private int commentId;
	@Column(name="reply")
	private String reply;
	@Column(name="reg_date")
	private String regDate;
	@Column(name="del_date")
	private String delDate;
	@Column(name="status")
	private boolean status;
	public CommentReply() {
		this.id = 0;
		this.commentId = 0;
		this.reply = "";
		this.regDate = "";
		this.delDate = "1111-11-11";
		this.status = false;
	}
	public CommentReply(int id, int commentId, String reply, String regDate, String delDate, boolean status) {
		this.id = id;
		this.commentId = commentId;
		this.reply = reply;
		this.regDate = regDate;
		this.delDate = delDate;
		this.status = status;
	}
	public CommentReply(CommentReply cmtRep) {
		this.id = cmtRep.id;
		this.commentId = cmtRep.commentId;
		this.reply = cmtRep.reply;
		this.regDate = cmtRep.regDate;
		this.delDate = cmtRep.delDate;
		this.status = cmtRep.status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getDelDate() {
		return delDate;
	}
	public void setDelDate(String delDate) {
		this.delDate = delDate;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "CommentReply [id=" + id + ", commentId=" + commentId + ", reply=" + reply + ", regDate=" + regDate
				+ ", delDate=" + delDate + ", status=" + status + "]";
	}
}
