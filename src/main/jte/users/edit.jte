@import org.example.hexlet.NamedRoutes
@import org.example.hexlet.dto.users.UserPage
@param UserPage page

@template.layout.page(
    content = @`
        <form action="${NamedRoutes.editUserPath(page.getUser().getId())}" method="post">
            <div>
                <label>
                Имя
                <input type="text" name="firstName" value="${page.getUser().getFirstName()}"/>
                </label>
            </div>
            <div>
                <label>
                Фамилия
                <input type="text" name="lastName" value="${page.getUser().getLastName()}"/>
                </label>
            </div>
            <div>
                <label>
                Email
                <input type="email" required name="email" value="${page.getUser().getEmail()}"/>
                </label>
            </div>
            <input type="submit" value="Обновить" />
        </form>
    `
)