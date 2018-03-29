/**
 * The purpose of this class is to hold the chapter heading metadata so that it is easy to understand and that there is a clear mapping between the input file and the schema.  Eventually, this will likely become a sort of database table definition.
 */
public class ChapterHeading{

    public ChapterHeading(String name, Integer chapter) {
        this.setName(name);
        this.setChapter(chapter);
    }
    private String Name;
    private Integer Chapter;
    private String Book;
    private String Description;


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getChapter() {
        return Chapter;
    }

    public void setChapter(Integer chapter) {
        Chapter = chapter;
    }

    public String getBook() {
        return Book;
    }

    public void setBook(String book) {
        Book = book;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}