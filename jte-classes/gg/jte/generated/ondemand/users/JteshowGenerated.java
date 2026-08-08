package gg.jte.generated.ondemand.users;
import org.example.hexlet.NamedRoutes;
import org.example.hexlet.dto.users.UserPage;
public final class JteshowGenerated {
	public static final String JTE_NAME = "users/show.jte";
	public static final int[] JTE_LINE_INFO = {0,0,1,2,2,2,4,4,6,6,8,8,8,9,9,9,10,10,10,11,11,11,14,14,14,14,14,14,14,14,14,16,16,16,16,16,2,2,2,2};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, UserPage page) {
		jteOutput.writeContent("\r\n");
		gg.jte.generated.ondemand.layout.JtepageGenerated.render(jteOutput, jteHtmlInterceptor, new gg.jte.html.HtmlContent() {
			public void writeTo(gg.jte.html.HtmlTemplateOutput jteOutput) {
				jteOutput.writeContent("\r\n        <h2>Пользователь</h2>\r\n        <p>Id: ");
				jteOutput.setContext("p", null);
				jteOutput.writeUserContent(page.getUser().getId());
				jteOutput.writeContent("</p>\r\n        <p>Имя: ");
				jteOutput.setContext("p", null);
				jteOutput.writeUserContent(page.getUser().getFirstName());
				jteOutput.writeContent("</p>\r\n        <p>Фамилия: ");
				jteOutput.setContext("p", null);
				jteOutput.writeUserContent(page.getUser().getLastName());
				jteOutput.writeContent("</p>\r\n        <p>E-mail: ");
				jteOutput.setContext("p", null);
				jteOutput.writeUserContent(page.getUser().getEmail());
				jteOutput.writeContent("</p>\r\n\r\n        <div>\r\n            <a");
				var __jte_html_attribute_0 = NamedRoutes.editUserPath(page.getUser().getId());
				if (gg.jte.runtime.TemplateUtils.isAttributeRendered(__jte_html_attribute_0)) {
					jteOutput.writeContent(" href=\"");
					jteOutput.setContext("a", "href");
					jteOutput.writeUserContent(__jte_html_attribute_0);
					jteOutput.setContext("a", null);
					jteOutput.writeContent("\"");
				}
				jteOutput.writeContent(">Редактировать</a>\r\n        </div>\r\n    ");
			}
		}, null);
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		UserPage page = (UserPage)params.get("page");
		render(jteOutput, jteHtmlInterceptor, page);
	}
}
