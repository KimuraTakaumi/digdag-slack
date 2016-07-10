package jp.techium.blog;

public class SlackInfo {
    private String channel;

    private String username;

    private String text;

    private String icon_emoji;

    public SlackInfo(String channel, String username, String text, String icon_emoji) {
        this.channel = channel;
        this.username = username;
        this.text = text;
        this.icon_emoji = icon_emoji;
    }
}
