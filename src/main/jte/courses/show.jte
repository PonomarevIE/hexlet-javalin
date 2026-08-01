@import org.example.hexlet.dto.courses.CoursePage
@param CoursePage page

@template.layout.page(
    content = @`
        <h2>Курс ${page.getCourse().getId()}. ${page.getCourse().getName()}</h2>
        <div>${page.getCourse().getDescription()}</div>
    `
)