package dev.library.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import dev.library.model.Book;
import dev.library.service.BookService;
import dev.library.util.collection.CheckingAuthorsCollection;
import dev.library.util.file.FileProcessing;
import dev.library.util.file.RegexProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import dev.library.model.Author;
import dev.library.service.AuthorService;
import dev.library.util.data.ListAuthors;
import dev.library.util.data.parse.JsonParse;

@Controller
@RequestMapping("/authors")
@PropertySource("classpath:general.properties")
public class AuthorController {
	@Autowired
	private AuthorService authorService;
	@Autowired
	private BookService bookService;
	@Value("${file.directory}")
	private String directory;

	/* Карта для хранения результата из другого метода */
	private Map<String, List<Author>> authorsMap;
	/* Строковая переменная для хранения сообщения из другого метода */
	private String errorMessage;

	/**
	 * Получение страницы со списком всех авторов
	 */
	@GetMapping("/all")
	public String showAllAuthors(Model model) {
		authorsMap = null;

		if (errorMessage != null) {
			String message = errorMessage;
			model.addAttribute("errorMessage", message);
			errorMessage = null;
		}

		List<Author> authors = authorService.readAllAuthors();
		List<Book> books = bookService.getAllBooks();
		Map<String, List<Author>> map = CheckingAuthorsCollection.checkAuthorsFromDb(authors, books);
		List<Author> permit = map.get("permit");
		List<Author> restrict = map.get("restrict");
		model.addAttribute("permit", permit);
		model.addAttribute("restrict", restrict);

		return "authors/showAllAuthors";
	}

	/**
	 * Загрузка файла переданного через HTML-форму
	 */
	@PostMapping("/upload")
	public String uploadJsonFileWithAuthors(@RequestParam("file") MultipartFile file) {
		FileProcessing.createDirectory(directory);
		
		String filename = file.getOriginalFilename();
		String pathToFile = directory + filename;

		while (FileProcessing.isExists(pathToFile)) {
			pathToFile = RegexProcessing.renameFileCount(pathToFile, directory);
		}

		try {
			FileProcessing.saveFile(file, pathToFile);
			Optional<ListAuthors> optionalAuthors = JsonParse.parseAuthorsFromJSON(pathToFile);
			if (optionalAuthors.isPresent()) {
				ListAuthors listAuthors = optionalAuthors.get();
				List<Author> authors = listAuthors.authors();
				List<Author> authorsFromDb = authorService.readAllAuthors();
				authorsMap = CheckingAuthorsCollection.checkAuthorsFromJsonFile(authors, authorsFromDb);
				List<Author> ok = authorsMap.get("ok");
				if (!ok.isEmpty()) {
					authorService.addAuthorsFromJson(ok);
				}
			} else {
				errorMessage = "Ошибка парсинга JSON-файла";
			}
		} finally {
			FileProcessing.deleteFile(pathToFile);
		}
		
		return "redirect:/authors/upload/info";
	}

	/**
	 * Получение страницы с информацией о загруженном файле
	 */
	@GetMapping("/upload/info")
	public String informationAboutAuthorsFromJsonFile(Model model) {
		if (authorsMap == null) {
			if (errorMessage != null) {
				String message = errorMessage;
				model.addAttribute("errorMessage", message);
			} else {
				model.addAttribute("errorMessage", "Ошибка запроса");
			}
		} else {
			List<Author> ok = authorsMap.get("ok");
			List<Author> notOk = authorsMap.get("notOk");
			model.addAttribute("ok", ok);
			model.addAttribute("notOk", notOk);
			authorsMap = null;
		}
		
		return "authors/informationAboutUploadedAuthors";
	}

	/**
	 * Удаление автора по идентификатору
	 */
	@DeleteMapping("/delete/{id}")
	public String deleteAuthor(@PathVariable("id") long id) {
		boolean result = authorService.deleteAuthor(id);
		if (!result) {
			errorMessage = String.format("Ошибка удаления, автора с идентификатором %d не существует", id);
		}
		return "redirect:/authors/all";
	}
}
