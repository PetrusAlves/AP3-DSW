package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import entidade.Caneta;
import util.JPAUtil;

public class CanetaDao {
	
	
	public static void salvar(Caneta caneta) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		em.persist(caneta);
		em.getTransaction().commit();
		em.close();
	}

	public static void excluir(Integer id) {
		EntityManager em = JPAUtil.criarEntityManager();
		em.getTransaction().begin();
		Caneta caneta = em.find(Caneta.class, id);
		em.remove(caneta);
		em.getTransaction().commit();
		em.close();
	}
	
	public static List<Caneta> listar(){
		EntityManager em = JPAUtil.criarEntityManager();
		try {
			Query query = em.createQuery("SELECT c FROM Caneta c");
			List<Caneta> resultado = query.getResultList();
			return resultado;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}
	
	 public static List<Caneta> contarCanetasPorCor() {
	        EntityManager em = JPAUtil.criarEntityManager();
	        
	        // Contar canetas azuis
	        Query queryAzul = em.createQuery("SELECT COUNT(c) FROM Caneta c WHERE c.cor = :cor");
	        queryAzul.setParameter("cor", "azul");
	        Long quantidadeAzul = (Long) queryAzul.getSingleResult();

	        // Contar canetas pretas
	        Query queryPreto = em.createQuery("SELECT COUNT(c) FROM Caneta c WHERE c.cor = :cor");
	        queryPreto.setParameter("cor", "preto");
	        Long quantidadePreto = (Long) queryPreto.getSingleResult();

	        // Contar canetas vermelhas
	        Query queryVermelho = em.createQuery("SELECT COUNT(c) FROM Caneta c WHERE c.cor = :cor");
	        queryVermelho.setParameter("cor", "vermelho");
	        Long quantidadeVermelho = (Long) queryVermelho.getSingleResult();

	        // Criar objetos Caneta para armazenar as contagens
	        Caneta canetaAzul = new Caneta();
	        canetaAzul.setCor("azul");
	        canetaAzul.setQuantidade(quantidadeAzul);

	        Caneta canetaPreto = new Caneta();
	        canetaPreto.setCor("preto");
	        canetaPreto.setQuantidade(quantidadePreto);

	        Caneta canetaVermelho = new Caneta();
	        canetaVermelho.setCor("vermelho");
	        canetaVermelho.setQuantidade(quantidadeVermelho);

	        // Retornar a lista de canetas com as contagens
	        return List.of(canetaAzul, canetaPreto, canetaVermelho);
	    }
}