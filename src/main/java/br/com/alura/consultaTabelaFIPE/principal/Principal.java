package br.com.alura.consultaTabelaFIPE.principal;

import br.com.alura.consultaTabelaFIPE.model.Modelos;
import br.com.alura.consultaTabelaFIPE.model.Veiculos;
import br.com.alura.consultaTabelaFIPE.service.ConsumoApi;
import br.com.alura.consultaTabelaFIPE.service.ConverteDados;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Principal {

    private Scanner userInputs = new Scanner(System.in);
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private ConsumoApi consomeAPI = new ConsumoApi();
    private ConverteDados converteDados = new ConverteDados();
    private String json;

    public void printMenu() {
        System.out.println("""
                ****** Menu de busca Tabela Fipe ******
                Digite o tipo de veiculo entre as opcoes:
                * CARROS
                * MOTOS
                * CAMINHOES""");
        System.out.println(">>>>>>>> ");

        var tipoBuscado = userInputs.nextLine();
        if(tipoBuscado.toLowerCase().contains("carr")){
            tipoBuscado = "carros/marcas";
        } else if(tipoBuscado.toLowerCase().contains("mot")){
            tipoBuscado = "motos/marcas";
        } else { tipoBuscado = "caminhoes/marcas";}

        json = consomeAPI.obterDados(URL_BASE+tipoBuscado);
        //System.out.println(json);

        // Convertendo os dados vindos da api via json para uma lista da classe personalizada veiculos.
        var marcas = converteDados.obterLista(json, Veiculos.class);

        //Usando um stream para mappear a lista.
        Stream marcasStream = marcas.stream();
        marcasStream.sorted(Comparator.comparing(Veiculos::codigo)).forEach(System.out::println);

        //Implementando a selecao do usuario via codigo do veiculo.
        System.out.println("\nDigite o codigo da marca desejada: ");
        System.out.println(">>>>>>>>");
        var codVeiculoBuscado = userInputs.nextLine();
        var urlModelo = URL_BASE+tipoBuscado+"/"+codVeiculoBuscado+"/modelos";
        System.out.println(urlModelo);
        json = consomeAPI.obterDados(urlModelo);

        System.out.println("Modelos: ");
        var modelosBucados = converteDados.converteDados(json, Modelos.class);
        modelosBucados.modelos().stream()
                .sorted(Comparator.comparing(Veiculos::codigo))
                .forEach(System.out::println);

        System.out.println("\nDigite o nome ou parte do nome do modelo: ");
        var modelosBuscadosAgain = userInputs.nextLine();
        List<Veiculos> listaModelo = modelosBucados.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(modelosBuscadosAgain.toLowerCase()))
                .collect(Collectors.toList());
        System.out.println(listaModelo);

        System.out.println("\nDigite o codigo do modelo desejado: ");
        var modelosBuscadosAgainAgain = userInputs.nextLine();
        urlModelo = URL_BASE+tipoBuscado+"/"+codVeiculoBuscado+"/modelos";

    }
}
