import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.*;

public class FileHandler {
    private File file;
    private String outFilePath;

    FileHandler(File f1, String fileName) {
        this.file = f1;
        this.outFilePath = fileName;
    }

    public void startHandling(GUI app) throws IOException, UnirestException {
        try {
            app.loggingArea.removeAll();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while (reader.ready()) {
                Thread.sleep(1000);
                String line = reader.readLine();
                app.loggingArea.append("Обрабатывается строка.............." + line + "\n");
                Thread.sleep(1000);
                Response response = new Response(line);
                String resultOfResponse = response.getResponse();
                app.loggingArea.append("Получен результат:        " + resultOfResponse + "\n");
                writeToFile(line + ";" + resultOfResponse);
            }
            reader.close();
        } catch (IOException e) {
            throw new IOException("Ошибка чтения из файла");
        } catch (UnirestException e1) {
            throw new UnirestException("Ошибка запроса");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(String stringForWrite) throws IOException {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outFilePath, true));
            writer.write(stringForWrite);
            writer.close();
        } catch (IOException e) {
            throw new IOException("Ошибка записи в файл");
        }
    }
}
