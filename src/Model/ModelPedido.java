package Model;

import java.util.Date;
import java.util.List;

public class ModelPedido {
    private int id;
    private int idCliente;
    private int idFuncionario;
    private Date data;
    private double valorTotal;
    private List<ModelPedidoProduto> produtos;

    public ModelPedido() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public int getIdFuncionario() { return idFuncionario; }
    public void setIdFuncionario(int idFuncionario) { this.idFuncionario = idFuncionario; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public double getValorTotal() { return valorTotal; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }

    public List<ModelPedidoProduto> getProdutos() { return produtos; }
    public void setProdutos(List<ModelPedidoProduto> produtos) { this.produtos = produtos; }
}
