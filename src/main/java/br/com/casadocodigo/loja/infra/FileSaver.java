package br.com.casadocodigo.loja.infra;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileSaver {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;

	public String write(String baseFolder, MultipartFile file) {

		try {

			String realPath = request.getServletContext().getRealPath("/" + baseFolder);
			String path = realPath + "/" + file.getOriginalFilename();
			file.transferTo(new File(path));
			return baseFolder + "/" + file.getOriginalFilename();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public void gerarZipRecursivo(String path) throws IOException, ServletException {
		byte[] buffer = new byte[4096]; // Create a buffer for copying
		int bytesRead;
		OutputStream out;
		response.setContentType("application/zip");
		response.setHeader("Content-Disposition", "attachment; filename=\"teste.zip\"");
		out = response.getOutputStream();
		ZipOutputStream outZip = new ZipOutputStream(out);
		List<File> arquivos = new ArrayList<File>();
		File dir = new File(path);
		if (dir.isDirectory()) {
			File[] sub = dir.listFiles();
			for (File f : sub) {
				if (f.isDirectory()) {
					System.out.println(f);
				} else {
					arquivos.add(f);
				}
			}
		}
		for (Iterator<File> it = arquivos.iterator(); it.hasNext();) {
			File file = it.next();
			FileInputStream fin = new FileInputStream(file);
			byte fileContent[] = new byte[(int) file.length()];
			fin.read(fileContent);
			ByteArrayInputStream in = new ByteArrayInputStream(fileContent);
			ZipEntry entry = new ZipEntry(file.getName());
			outZip.putNextEntry(entry);
			while ((bytesRead = in.read(buffer)) != -1) {
				outZip.write(buffer, 0, bytesRead);
			}
			in.close();

		}
		{
			String texto = "Arquivos gerados recursivamente a partir da pasta " + path;
			ByteArrayInputStream in = new ByteArrayInputStream(texto.getBytes());
			ZipEntry entry = new ZipEntry("LEIAME" + ".txt");
			outZip.putNextEntry(entry);
			while ((bytesRead = in.read(buffer)) != -1) {
				outZip.write(buffer, 0, bytesRead);
			}
			in.close();
		}

		outZip.close();
		

	}

}