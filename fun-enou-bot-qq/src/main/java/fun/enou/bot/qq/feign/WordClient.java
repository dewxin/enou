package fun.enou.bot.qq.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import fun.enou.core.msg.EnouMsgJson;

@FeignClient("SERVICE-ALPHA")
public interface WordClient {

	@GetMapping("/word/random")
	EnouMsgJson getOneRandomWord();
}
