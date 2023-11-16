package PhishingUniv.Phinocchio.domain.Doubt.constant;

public enum DoubtLevel {

    SAFETY_LEVEL("안전", 0),
    DOUBT_LEVEL("의심", 1),
    WARNING_LEVEL("경고", 2),
    DANGER_LEVEL("위험", 3);

    private String levelName;
    private int level;

    DoubtLevel(String levelName, int level) {
        this.levelName = levelName;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public String getLevelName() {
        return levelName;
    }
}