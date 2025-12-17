package cn.itcast.tools;

import cn.itcast.dto.PackageDataDTO;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class PackageDataTools {

    @Tool(description = "查询移动套餐数据列表")
    public List<PackageDataDTO> getPackageDataList() {
        // 经济卡
        PackageDataDTO economy = PackageDataDTO.builder().packageName("经济卡").monthlyFee("39").data("10GB").callDuration(100).extraServices("无").contractPeriod(0).promotion("首月半价").build();

        // 畅享全家享
        PackageDataDTO familyPlan = PackageDataDTO.builder().packageName("畅享全家享").monthlyFee("99").data("50GB").callDuration(500).extraServices("200M宽带+2张副卡").contractPeriod(12).promotion("送视频会员月卡").build();

        // 全球通尊享
        PackageDataDTO globalPremium = PackageDataDTO.builder().packageName("全球通尊享").monthlyFee("199").data("不限量").callDuration(2000).extraServices("国际漫游+5G优先").contractPeriod(24).promotion("机场贵宾厅2次/年").build();

        // 学生青春卡
        PackageDataDTO studentPlan = PackageDataDTO.builder().packageName("学生青春卡").monthlyFee("59").data("30GB").callDuration(200).extraServices("校园网加速").contractPeriod(6).promotion("免流特定APP").build();

        // 可以存入List集合方便后续使用
        return Arrays.asList(economy, familyPlan, globalPremium, studentPlan);
    }
}