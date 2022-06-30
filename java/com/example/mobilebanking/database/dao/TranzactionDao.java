package com.example.mobilebanking.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mobilebanking.database.models.Transaction;
import java.util.ArrayList;
import java.util.List;

@Dao
public interface TranzactionDao {

    @Query("SELECT * from transactions where idUser=:idUser")
    List<Transaction> tranzactionsByUser(long idUser);

    @Query("select * from transactions")
    List<Transaction> getAllTransactions();

    @Insert
    long insert(Transaction tranzaction);

    @Delete
    int delete(Transaction tranzaction);

    @Update
    void update(Transaction tranzaction);

    @Query("select * from transactions where idUser=:idUser and amount> :amount")
    List<Transaction> tranzactionsByAmount(long idUser,Integer amount);

    @Query("select beneficiaryName from transactions where idUser= :idUser and status='Send'")
    List<String> transactionsSend(long idUser);

    @Query("select SUM(amount) from transactions where idUser=:idUser and status='Send'")
    Integer sumAmount(long idUser);

    @Query("select SUM(amount) from transactions where idUser=:idUser and status='Ask'")
    Integer sumAsk(long idUser);
}
