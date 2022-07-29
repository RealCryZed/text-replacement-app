package app;

public class File {

    private String path;
    private String beforeText;
    private String afterText;

    public File(String path, String beforeText, String afterText) {
        this.path = path;
        this.beforeText = beforeText;
        this.afterText = afterText;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getBeforeText() {
        return beforeText;
    }

    public void setBeforeText(String beforeText) {
        this.beforeText = beforeText;
    }

    public String getAfterText() {
        return afterText;
    }

    public void setAfterText(String afterText) {
        this.afterText = afterText;
    }
}
