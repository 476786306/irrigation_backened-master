package com.libv.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("question")
public class Question implements Serializable {

  private long id;

  private String variety;

  private String detail;

  private String pictureUrl;

  private String reply;

  private String replyPictureUrl;

  @TableField("is_rejected")
  private String whetherRejected;

  private long isRejected;

  private long questionerId;

  private long answererId;

  @TableField(fill = FieldFill.INSERT)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createTime;

  @TableField(fill = FieldFill.INSERT_UPDATE)
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date updateTime;


}
