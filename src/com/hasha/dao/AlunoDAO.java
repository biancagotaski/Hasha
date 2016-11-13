package com.hasha.dao;

import com.hasha.entity.Aluno;
import com.hasha.factory.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Franclin Sousa on 09/11/2016.
 *
 * @author Franclin Sousa
 */
public class AlunoDAO {

    Connection connection;
    Aluno aluno;

    public AlunoDAO() {
        connection = new ConnectionFactory().getConnection();
    }

    public Integer adicionar(Aluno _aluno) throws SQLException {

        PreparedStatement ps;

        String sql = "insert into alunos (nome, nota_av1, nota_av2) values (?,?,?);";

        ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, _aluno.getNome());
        ps.setDouble(2, _aluno.getNota1());
        ps.setDouble(3, _aluno.getNota2());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();

        Integer lastId = null;

        if (rs.next()) {
            lastId = rs.getInt(1);
        }

        ps.close();

        return lastId;
    }

    public Aluno findAluno(int _id) throws Exception {

        PreparedStatement ps;
        Aluno aluno = null;

        String sql = "select * from alunos where id = ?";

        ps = connection.prepareStatement(sql);
        ps.setInt(1, _id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            aluno = new Aluno();
            aluno.setNome(rs.getString("nome"));
            aluno.setNota1(rs.getDouble("nota_av1"));
            aluno.setNota2(rs.getDouble("nota_av2"));
        }

        if (aluno == null) {
            throw new Exception("Código do aluno " + _id + " não encontrado.");
        }

        return aluno;
    }

    public ArrayList<Aluno> listaAlunos() throws SQLException {

        PreparedStatement ps;
        ArrayList<Aluno> alunos = new ArrayList<>();
        String sql = "select * from alunos";

        ps = connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Aluno aluno = new Aluno();
            aluno.setNome(rs.getString("nome"));
            aluno.setNota1(rs.getDouble("nota_av1"));
            aluno.setNota2(rs.getDouble("nota_av2"));
            alunos.add(aluno);
        }

        return alunos;
    }

    @Override
    protected void finalize() throws Throwable {
        connection.close();
        super.finalize();
    }

}
