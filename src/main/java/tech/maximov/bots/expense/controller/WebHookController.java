package tech.maximov.bots.expense.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import tech.maximov.bots.expense.bot.ExpensesBot;
import tech.maximov.bots.expense.config.ApiSearchConfig;

import java.util.Random;

@Slf4j
@RestController
public class WebHookController {
    private final ExpensesBot expensesBot;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String requestUrl;

    public WebHookController(ExpensesBot expensesBot, ApiSearchConfig apiSearchConfig) {
        this.expensesBot = expensesBot;
        requestUrl = "https://www.googleapis.com/customsearch/v1?key=" + apiSearchConfig.getApiToken() +
                "&cx=" + apiSearchConfig.getContext() + "&searchType=image&num=1";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return expensesBot.onWebhookUpdateReceived(update);
    }

    @RequestMapping(value = "/get/{request}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> search(@PathVariable(value = "request") String request) {
        var jsonString = restTemplate.getForObject(requestUrl + "&q=" + request, String.class);
        var totalResults = new Gson().fromJson(jsonString, JsonObject.class)
                .getAsJsonObject("searchInformation")
                .get("totalResults").getAsInt();

        var random = new Random();
        int itemNum;
        if (totalResults <= 100) {
            itemNum = random.nextInt(totalResults - 1);
        } else {
            itemNum = random.nextInt(99);
        }

        var headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        jsonString = restTemplate.getForObject(requestUrl + "&start=" + itemNum + "&q=" + request, String.class);
        var items = new Gson().fromJson(jsonString, JsonObject.class).get("items").getAsJsonArray();
        var element = items.get(0).getAsJsonObject();

        var image = restTemplate.getForObject(element.get("link").getAsString(), byte[].class);
        var mime = element.get("mime").getAsString();
        headers.setContentType(MediaType.parseMediaType(mime));
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
}
