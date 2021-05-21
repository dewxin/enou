package fun.enou.bot.qq.feign;

import com.github.dewxin.generated.auto_client.AlphaClient;

import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(value="SERVICE-ALPHA", contextId = "feign-alpha")
public interface AlphaFeignClient extends AlphaClient {

}
