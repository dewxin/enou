package fun.enou.alpha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fun.enou.alpha.dto.dtoweb.DtoWebWord;
import fun.enou.alpha.service.IWordService;
import fun.enou.core.msg.EnouMessageException;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	
	@Autowired
	private IWordService wordService;

	@GetMapping("/word/{word}")
	public ResponseEntity<DtoWebWord> getWordInfo(@PathVariable String word) throws EnouMessageException {
		DtoWebWord webWord = wordService.getWebWord(word);
		return ResponseEntity.ok(webWord);
	}
	
	@GetMapping("/word")
	public ResponseEntity<DtoWebWord> getWord(@RequestParam(value = "word") String word) throws EnouMessageException {
		DtoWebWord webWord = wordService.getWebWord(word);
		return ResponseEntity.ok(webWord);
	}

}
