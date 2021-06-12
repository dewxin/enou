package fun.enou.alpha.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fun.enou.alpha.dto.dtoweb.DtoWebWord;
import fun.enou.alpha.dto.dtoweb.DtoWebWordList;
import fun.enou.alpha.service.WordService;
import fun.enou.core.msg.AutoWrapMsg;
import fun.enou.core.msg.EnouMessageException;

@RestController
@RequestMapping("/api")
@AutoWrapMsg
public class ApiController {
	
	
	@Autowired
	private WordService wordService;

	@GetMapping("/word/{word}")
	public ResponseEntity<DtoWebWord> getWordInfo(@PathVariable String word) throws EnouMessageException {
		word = word.toLowerCase();
		DtoWebWord webWord = wordService.getWebWord(word);
		return ResponseEntity.ok(webWord);
	}
	
	@GetMapping("/word")
	public ResponseEntity<DtoWebWord> getWord(@RequestParam(value = "word") String word) throws EnouMessageException {
		word = word.toLowerCase();
		DtoWebWord webWord = wordService.getWebWord(word);
		return ResponseEntity.ok(webWord);
	}
	
	@GetMapping("/word/random")
	public ResponseEntity<DtoWebWordList> getRandomWord(@RequestParam(value = "count") Integer count) throws EnouMessageException {
		
		List<DtoWebWord> list = wordService.getWebWordList(count);
		DtoWebWordList wordList = new DtoWebWordList();
		wordList.setDtoWebWordList(list);

		return ResponseEntity.ok(wordList);
	}
	

}
