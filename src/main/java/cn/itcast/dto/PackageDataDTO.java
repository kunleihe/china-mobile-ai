package cn.itcast.dto;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 中国移动套餐实体类
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PackageDataDTO {

    @JsonPropertyDescription("套餐名称")
    private String packageName;

    @JsonPropertyDescription("月租（单位：元）")
    private String monthlyFee;

    @JsonPropertyDescription("流量（示例：10GB/不限量）")
    private String data;

    @JsonPropertyDescription("国内通话时长（单位：分钟，null表示不限量）")
    private Integer callDuration;

    @JsonPropertyDescription("附加服务（如宽带、副卡）")
    private String extraServices;

    @JsonPropertyDescription("合约期（单位：月，0表示无合约期）")
    private Integer contractPeriod;

    @JsonPropertyDescription("优惠活动（如首月半价）")
    private String promotion;

}
