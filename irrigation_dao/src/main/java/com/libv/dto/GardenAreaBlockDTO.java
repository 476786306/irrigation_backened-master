package com.libv.dto;

import com.libv.entity.GardenAreaBlock;
import lombok.Data;

import java.util.List;

/**
 * @author longjie
 * @since 2021/4/26 7:47
 */
@Data
public class GardenAreaBlockDTO {
    private Long areaId;
    private String gardenName;
    private List<GardenAreaBlock> blockDetailDTOList;
}
