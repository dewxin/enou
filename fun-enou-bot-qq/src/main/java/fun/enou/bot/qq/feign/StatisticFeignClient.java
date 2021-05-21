package fun.enou.bot.qq.feign;

import com.github.dewxin.generated.auto_client.StatisticClient;

import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(value="SERVICE-STATISTIC", contextId = "feign-statistic")
public interface StatisticFeignClient extends StatisticClient {
    
}
