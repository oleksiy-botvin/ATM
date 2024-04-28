package ua.edu.ztu.student.zipz221_boyu.data.use_case.impl;

import androidx.annotation.NonNull;

import java.util.List;

import io.reactivex.Single;
import ua.edu.ztu.student.zipz221_boyu.component_provider.ComponentProvider;
import ua.edu.ztu.student.zipz221_boyu.data.entity.Account;
import ua.edu.ztu.student.zipz221_boyu.data.use_case.WithoutArgUseCase;

public class GetAllAccountsUseCase implements WithoutArgUseCase<Single<List<Account>>> {
    @NonNull
    @Override
    public Single<List<Account>> invoke() {
        return ComponentProvider.Companion.getInstance()
                .getRepositories()
                .accounts()
                .invoke();
    }
}
