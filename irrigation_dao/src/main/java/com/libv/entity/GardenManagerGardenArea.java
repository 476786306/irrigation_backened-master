package com.libv.entity;

import com.baomidou.mybatisplus.annotation.*;

import lombok.Data;

@Data
@TableName("gardener_manager_garden_area")
public class GardenManagerGardenArea {

  private long id;

  private long managerId;

  private String gardenAreaId;

}
