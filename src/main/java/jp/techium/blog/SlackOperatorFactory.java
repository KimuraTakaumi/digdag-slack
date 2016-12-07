package jp.techium.blog;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.digdag.client.config.Config;
import io.digdag.spi.*;
import io.digdag.util.BaseOperator;

import java.nio.file.Path;

import static java.nio.charset.StandardCharsets.UTF_8;

public class SlackOperatorFactory
        implements OperatorFactory
    {
        private final TemplateEngine templateEngine;

    public SlackOperatorFactory(TemplateEngine templateEngine)
    {
        this.templateEngine = templateEngine;
    }

    public String getType()
    {
        return "slack";
    }

    @Override
    public Operator newOperator(Path workspacePath, TaskRequest request)
    {
        return new ExampleOperator(workspacePath, request);
    }

    private class ExampleOperator
            extends BaseOperator
    {
        public ExampleOperator(Path workspacePath, TaskRequest request)
        {
            super(workspacePath, request);
        }

        @Override
        public TaskResult runTask(TaskExecutionContext ctx)
        {
            Config params = request.getConfig().mergeDefault(
                    request.getConfig().getNestedOrGetEmpty("slack"));

            String message = workspace.templateCommand(templateEngine, params, null, UTF_8);
            String url = params.get("webhook", String.class);
            String channel = "#" + params.get("channel", String.class);
            String username = params.get("username", String.class);
            String icon_emoji = ":" + params.get("icon_emoji", String.class) + ":";

            System.out.println(url);
            System.out.println(message);
            System.out.println(channel);
            System.out.println(username);
            System.out.println(icon_emoji);

            Gson gson = new Gson();
            String payload = gson.toJson(new SlackInfo(channel,username,message,icon_emoji));
            System.out.println(payload);

            try {
                HttpResponse<String> result = Unirest.post(url)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                        .field("payload", payload).asString();
            } catch (UnirestException e) {
                e.printStackTrace();
            }
            return TaskResult.empty(request);
        }
    }
}
