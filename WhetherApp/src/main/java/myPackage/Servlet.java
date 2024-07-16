package myPackage;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String apiKey="eef3eda8870a103721ecaa79c3626474";
		String city=request.getParameter("city");
		String apiurl="https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+apiKey;
		URL url = new URL(apiurl);
		HttpURLConnection connection=(HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		InputStream stream = connection.getInputStream();
		InputStreamReader reader = new InputStreamReader(stream);
		StringBuilder SB = new StringBuilder();
		Scanner scanner = new Scanner(reader);
		while(scanner.hasNext()) {
			SB.append(scanner.nextLine());
		}
		scanner.close();
		
		
		
		  Gson gson = new Gson(); JsonObject jsonObject =(JsonObject) gson.fromJson(SB.toString(),
		  JsonObject.class);
		  
		  
		  
		  long dateTimestamp = jsonObject.get("dt").getAsLong() * 1000; 
		  String date =new Date(dateTimestamp).toString();
		  
		  
		  double temperatureKelvin =jsonObject.getAsJsonObject("main").get("temp").getAsDouble(); 
		  int temperatureCelsius = (int) (temperatureKelvin - 273.15);
		  
		  
		  int humidity = jsonObject.getAsJsonObject("main").get("humidity").getAsInt();
		  
		  
		  double windSpeed =
		  jsonObject.getAsJsonObject("wind").get("speed").getAsDouble();
		  
		  
		  String weatherCondition =
		  jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("main").
		  getAsString();
		  
		  request.setAttribute("date", date); request.setAttribute("city", city);
		  request.setAttribute("temperature", temperatureCelsius);
		  request.setAttribute("weatherCondition", weatherCondition);
		  request.setAttribute("humidity", humidity); 
		  request.setAttribute("windSpeed",windSpeed); 
		  request.setAttribute("weatherData",SB.toString());
		  request.getRequestDispatcher("index.jsp").forward(request, response);
		  
		 
	}

}
