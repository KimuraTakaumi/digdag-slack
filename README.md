# digdag-slack

## How to use

### file tree
```
.
├── digdag-slack          <-- This project release file
├── message.txt
└── hello_world.dig
```

### Create File 

[hello_world.dig]
```
_export:
  plugin:
    repositories:
      - file://${repository_path}
    dependencies:
      - jp.techium.blog:digdag-slack:0.1.0

+step1:
  slack>: message.txt
  webhook: https://hooks.slack.com/services/XXXXXXXX/XXXXXXXXX/XXXXXXXXXXXXXXXXXXXXXX      # <-- Slack Incoming WebHooks url
  channel: general
  username: webhookbot
  icon_emoji: ghost
```

[message.txt]
```
hello world!
```

### Run

```
$ digdag r hello_world.dig -p repository_path=/path/to/workspace/digdag-slack
```

