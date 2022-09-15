package Controller;

import Model.Product.IRepositoryProduct;
import Model.Product.Product;
import View.ViewProduct;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dgalvarez
 */
public class ControllerProduct implements ActionListener {

    private IRepositoryProduct repository;
    private ViewProduct viewProduct;

    private Integer CodeToUpdate = -1;

    public ControllerProduct(IRepositoryProduct repository, ViewProduct viewProduct) {

        this.viewProduct = viewProduct;
        this.repository = repository;
        addEvents();
        ToList();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewProduct.getAddProductButton()) {
            AddProduct();
            ToList();
        }

        if (e.getSource() == viewProduct.getDeleteProductButton()) {
            DeleteProduct();
            ToList();
        }

        if (e.getSource() == viewProduct.getUpdateProductButton()) {
            ShowUpdateProduct();
            ToList();
        }
        if (e.getSource() == viewProduct.getUpdateDialogButton()) {
            UpdateProduct();
            ToList();
        }

        if (e.getSource() == viewProduct.getInfoProductButton()) {
            showMessage("Informe",ReportProduct());
            ToList();
        }
        if (e.getSource() == viewProduct.getInfoProductButton()) {
            ReportProduct();
            ToList();
        }
        
    }

    private void addEvents() {
        this.viewProduct.getAddProductButton().addActionListener(this);
        this.viewProduct.getUpdateProductButton().addActionListener(this);
        this.viewProduct.getDeleteProductButton().addActionListener(this);

////////////////////////////////// Dialogs /////////////////////////////////////
        this.viewProduct.getInfoProductButton().addActionListener(this);
        this.viewProduct.getUpdateDialogButton().addActionListener(this);

    }

    //Create o ADD
    @Transactional
    private void AddProduct() {

        String name = this.viewProduct.getNameProductField().getText();
        String price = this.viewProduct.getPriceProductField().getText();
        String inventory = this.viewProduct.getInventoryProductField().getText();
        if (!name.equals("") && !price.equals("") && !inventory.equals("")) {
            Product productN = Product.createProduct(name, Double.parseDouble(price), Integer.parseInt(inventory));
            repository.save(productN);
            this.viewProduct.getNameProductField().setText("");
            this.viewProduct.getPriceProductField().setText("");
            this.viewProduct.getInventoryProductField().setText("");

        } else {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios", "Advertencia", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Update
    private void ShowUpdateProduct() {
        JTable ReportProduct = this.viewProduct.getReportTableProducts();
        Integer rowSelected = ReportProduct.getSelectedRow();

        //Show in the Dialog
        if (rowSelected > -1) {
            CodeToUpdate = (Integer) ReportProduct.getModel().getValueAt(ReportProduct.getSelectedRow(), 0);
            Product ProductToUpdate = this.repository.findById(CodeToUpdate).get();

            viewProduct.getNameToUpdateField().setText(ProductToUpdate.getName());
            viewProduct.getPriceToUpdateField().setText(ProductToUpdate.price + "");
            viewProduct.getInventoryToUpdateField().setText(ProductToUpdate.inventory + "");
            this.viewProduct.getUpdateDialog().setVisible(true);

        } else {
            showMessage("!!!ERROR!!!", "No ha seleccionado un elemento");
        }
    }

    private void UpdateProduct() {
        String name = this.viewProduct.getNameToUpdateField().getText();
        String price = this.viewProduct.getPriceToUpdateField().getText();
        String inventory = this.viewProduct.getInventoryToUpdateField().getText();
        Product ProductToUpdate = this.repository.findById(CodeToUpdate).get();

        if (!name.equals("") && !price.equals("") && !inventory.equals("")) {
            ProductToUpdate.setName(name);
            ProductToUpdate.setPrice(Double.parseDouble(price));
            ProductToUpdate.setInventory(Integer.parseInt(inventory));
            repository.save(ProductToUpdate);
            this.viewProduct.getUpdateDialog().setVisible(false);
            showMessage("Actualizado", "Prodcuto Actualizado Correctamente");

        } else {
            showMessage("!!!ERROR!!!", "No se encuentra el producto o los campos estan vacios");
        }
    }
    //Delete   

    private void DeleteProduct() {
        JTable ReportProduct = this.viewProduct.getReportTableProducts();
        Integer code = (Integer) ReportProduct.getModel().getValueAt(ReportProduct.getSelectedRow(), 0);
        
        if (code > -1) {
            repository.deleteById(code);
            showMessage("Informacion", "El producto fue borrado exitosamente");
        } 
        else{
            JOptionPane.showMessageDialog(null,"No se ha seleccionado ningun registro", "!!!ERROR!!!", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Research
    private void FindProduct() {
    }

    //Mostrar
    private List<Product> ToList() {

        List<Product> products = (List<Product>) repository.findAll();
        JTable report = this.viewProduct.getReportTableProducts();

        int row = 0;
        for (Product product : products) {
            report.setValueAt(product.code, row, 0);
            report.setValueAt(product.name, row, 1);
            report.setValueAt(product.price, row, 2);
            report.setValueAt(product.inventory, row, 3);
            row++;
        }

        for (int i = row; i < report.getRowCount(); i++) {
            report.setValueAt("", row, 0);
            report.setValueAt("", row, 1);
            report.setValueAt("", row, 2);
        }

        return products;
    }

    //Generate Report
    private String ReportProduct(){
        System.out.println(getMaxInventory());
        System.out.println(getMinInventory());
        System.out.println(getAverageInventory());
        System.out.println(getSumInventory());
        return "\nProducto de precio mayor: "+getMaxInventory()+"\n\nProducto precio menor: "+getMinInventory()
                +"\n\nPromedio precios: "+getAverageInventory()+"\n\nValor del inventario: "+ getSumInventory();
    }
    

    private void showMessage(String title, String message) {

        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);

    }
    //Consultas
    public Double getAverageInventory() {
        return repository.getAveragePrice();
    }

    public Double getSumInventory() {
        return repository.getSumPrice();
    }
    public String getMaxInventory() {
        return repository.getMaxPrice();
    }
    public String getMinInventory() {
        return repository.getMinPrice();
    }
    
    
}
