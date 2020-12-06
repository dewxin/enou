package fun.enou.alpha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fun.enou.alpha.dto.dtoweb.DtoWebWord;
import fun.enou.alpha.service.IWordService;
import lombok.extern.slf4j.Slf4j;

@Profile("dev")
@RestController
@RequestMapping("/dev/word")
@Slf4j
public class DevWordController {
	
	@Autowired
	private IWordService wordService;

	@PostMapping
	public void uploadWord(@RequestBody DtoWebWord webWord) {
		wordService.uploadWord(webWord);
	}

}
