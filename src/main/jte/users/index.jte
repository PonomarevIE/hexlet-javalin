@import org.example.hexlet.dto.users.UsersPage
@param UsersPage page

@template.layout.page(
    content = @`
        <h1>Пользователи</h1>

        <div>
            <a href="/users/build">Зарегистрировать нового пользователя</a>
        </div>

        <form action="/users" method="get">
            <input type="search" required name="term">
            <input type="submit" value="Искать">
        </form>

        <table class="table table-striped">
            @for(var user : page.getUsers())
                <tr>
                    <td>
                        ${user.getId()}
                    </td>
                    <td>
                        <a href="/users/${user.getId()}">${user.getFirstName()} ${user.getLastName()}</a>
                    </td>
                </tr>
            @endfor
        </table>
    `,
    footer = @`
        <p>Thanks for visiting, come again soon!</p>
    `
)