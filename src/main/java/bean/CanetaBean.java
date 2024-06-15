package bean;

import java.util.List;
import java.util.Objects;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import dao.CanetaDao;
import entidade.Caneta;

@ManagedBean
@SessionScoped
public class CanetaBean {
    private Caneta caneta = new Caneta();
    private List<Caneta> listagem;
    private Caneta canetaSelecionada;

    public void salvar() {
        try {
        	if(validarSalvar()) {
        		if(validarLetras()) {
            CanetaDao.salvar(caneta);
            caneta = new Caneta();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!", "Caneta cadastrada com sucesso!"));
        	}
        	}
        }catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void excluir(Integer id) {
        try {
            CanetaDao.excluir(id);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Caneta com sucesso!", ""));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível excluir esse item: " + e.getMessage()));
            System.out.println("Erro ao excluir: " + e.getMessage());
        }
    }
    public List<Caneta> listar() {
        listagem = CanetaDao.listar();
        return listagem;
    }
    
    public void contarCanetasPorCor() {
        try {
            List<Caneta> resultados = CanetaDao.contarCanetasPorCor();
            FacesContext facesContext = FacesContext.getCurrentInstance();
            for (Caneta resultado : resultados) {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Total: ", resultado.getQuantidade() + " canetas " + resultado.getCor()));
            }
        } catch (Exception e) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Não foi possível contar as canetas por cor."));
        }
    }

    public void selecionarCaneta(Caneta caneta) {
        this.canetaSelecionada = caneta;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", 
                "ID: " + caneta.getId() + ", Marca: " + caneta.getMarca() + ", Modelo: " + caneta.getModelo() + ", Cor: " + caneta.getCor()));
    }
    
    private boolean validarSalvar() {
		if (caneta.getMarca().equals("") || Objects.isNull(caneta.getMarca())) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "A Marca da caneta é obrigatório."));
			return false;
		}
		if (caneta.getModelo().equals("") || Objects.isNull(caneta.getModelo())) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "O Modelo da caneta é obrigatório."));
			return false;
		}
		if (caneta.getCor().equals("") || Objects.isNull(caneta.getCor())) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "A Cor da caneta é obrigatório."));
			return false;
		}

		
		return true;
	}
    
    public boolean validarLetras() {
        if (!caneta.getMarca().matches("^[A-Za-z ]+$")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Marca: Só é permitido letras."));
            return false;
        }
        if (!caneta.getModelo().matches("^[A-Za-z ]+$")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Modelo: Só é permitido letras."));
            return false;
        }
        if (!caneta.getCor().matches("^[A-Za-z ]+$")) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Cor: Só é permitido letras."));
            return false;
        }
        return true;
    }

    
    public List<Caneta> getListagem() {
        return listagem;
    }

    public void setListagem(List<Caneta> listagem) {
        this.listagem = listagem;
    }

    public Caneta getCaneta() {
        return caneta;
    }

    public void setCaneta(Caneta caneta) {
        this.caneta = caneta;
    }
}
