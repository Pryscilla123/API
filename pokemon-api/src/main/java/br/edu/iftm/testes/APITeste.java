package br.edu.iftm.testes;

import java.io.IOException;

import org.json.JSONObject;

import br.edu.iftm.api.Api;
import br.edu.iftm.api.JsonParser;

public class APITeste {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Api api = new Api("https://pokeapi.co/api/v2/pokemon/20");
		JSONObject item = JsonParser.parseToObject(api.executar());
		System.out.println(item.get("species"));
	}

}
