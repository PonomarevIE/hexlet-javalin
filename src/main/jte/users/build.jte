@import org.example.hexlet.NamedRoutes
@import org.example.hexlet.dto.users.BuildUserPage
@param BuildUserPage page

@template.layout.page(
    content = @`
         @if(page.getErrors() != null)
             <ul>
                 @for(var validator : page.getErrors().values())
                     @for(var error : validator)
                         <li>${error.getMessage()}</li>
                     @endfor
                 @endfor
             </ul>
         @endif

        <form action="${NamedRoutes.usersPath()}" method="post">
            <div>
                <label>
                Имя
                <input type="text" name="firstName" value="${page.getFirstName()}"/>
                </label>
            </div>
            <div>
                <label>
                Фамилия
                <input type="text" name="lastName" value="${page.getLastName()}"/>
                </label>
            </div>
            <div>
                <label>
                Email
                <input type="email" required name="email" value="${page.getEmail()}"/>
                </label>
            </div>
            <div>
                <label>
                Пароль
                <input type="password" required name="password" />
                </label>
            </div>
            <input type="submit" value="Зарегистрировать" />
        </form>
    `
)