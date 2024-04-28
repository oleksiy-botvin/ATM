package ua.edu.ztu.student.zipz221_boyu.component_provider.components;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Single;
import ua.edu.ztu.student.zipz221_boyu.data.entity.Account;
import ua.edu.ztu.student.zipz221_boyu.data.entity.card.CardNumber;
import ua.edu.ztu.student.zipz221_boyu.data.repository.WithArgRepository;
import ua.edu.ztu.student.zipz221_boyu.data.repository.WithoutArgRepository;

public interface Repositories {

    @NonNull
    WithoutArgRepository<Single<List<Account>>> accounts();
    @NonNull
    WithArgRepository<CardNumber, Single<Account>> account();
}
