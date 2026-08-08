@import org.example.hexlet.NamedRoutes
@import org.example.hexlet.dto.users.UserPage
@param UserPage page

@template.layout.page(
    content = @`
        <h2>Пользователь</h2>
        <p>Id: ${page.getUser().getId()}</p>
        <p>Имя: ${page.getUser().getFirstName()}</p>
        <p>Фамилия: ${page.getUser().getLastName()}</p>
        <p>E-mail: ${page.getUser().getEmail()}</p>

        <div>
            <a href="${NamedRoutes.editUserPath(page.getUser().getId())}">Редактировать</a>
        </div>
    `
)