package fun.enou.bot.qq.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("SERVICE-ALPHA")
public interface WordClient {

	@GetMapping("/word/random")
	ResponseEntity<String> getOneRandomWord();
}
