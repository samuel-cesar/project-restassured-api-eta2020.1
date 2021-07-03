package models;

public class Product {

    public String nome;
    public String preco;
    public String descricao;
    public String quantidade;
    public String productID;

    public Product(String nome, String preco, String descricao, String quantidade) {
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.quantidade = quantidade;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }
}
