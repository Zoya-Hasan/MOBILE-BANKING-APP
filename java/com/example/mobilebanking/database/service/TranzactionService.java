package com.example.mobilebanking.database.service;

import android.content.Context;
import android.os.AsyncTask;

import com.example.mobilebanking.database.DatabaseManager;
import com.example.mobilebanking.database.dao.TranzactionDao;
import com.example.mobilebanking.database.models.Transaction;

import java.util.List;

public class TranzactionService {
    private static TranzactionDao transactionDao;

    public static class GetAllTransactions
            extends AsyncTask<Void, Void, List<Transaction>> {

        public GetAllTransactions(Context context) {
            transactionDao = DatabaseManager
                    .getInstance(context)
                    .getTranzactionDao();
        }

        @Override
        protected List<Transaction> doInBackground(Void... voids) {
            return transactionDao.getAllTransactions();
        }
    }


    public static class Insert extends
            AsyncTask<Transaction, Void, Transaction> {
        public Insert(Context context) {
            transactionDao = DatabaseManager
                    .getInstance(context)
                    .getTranzactionDao();
        }

        @Override
        protected Transaction doInBackground(Transaction... tranzactions) {
            if (tranzactions == null || tranzactions.length != 1) {
                return null;
            }
            Transaction tranzaction = tranzactions[0];
            long id = transactionDao.insert(tranzaction);
            if (id != -1) {
                tranzaction.setIdTranzactie(id);
                return tranzaction;
            }
            return null;
        }
    }

    public static class Delete extends
            AsyncTask<Transaction, Void, Integer> {
        public Delete(Context context) {
            transactionDao = DatabaseManager
                    .getInstance(context)
                    .getTranzactionDao();
        }

        @Override
        protected Integer doInBackground(Transaction... transactions) {
            if (transactions == null || transactions.length != 1) {
                return -1;
            }
            return transactionDao.delete(transactions[0]);
        }
    }
}
