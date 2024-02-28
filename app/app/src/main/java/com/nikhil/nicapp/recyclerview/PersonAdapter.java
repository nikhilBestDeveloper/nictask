package com.nikhil.nicapp.recyclerview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nikhil.nicapp.PersonFormActivity;
import com.nikhil.nicapp.databinding.ItemPersonBinding;
import com.nikhil.nicapp.databinding.ItemShimmerBinding;
import com.nikhil.nicapp.model.Person;

import java.util.List;


public class PersonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private List<Person> personList;

    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_SHIMMER = 1;

    private boolean showShimmer = true;

    public PersonAdapter(Context context, List<Person> personList) {
        this.context = context;
        this.personList = personList;
    }

    @Override
    public int getItemViewType(int position) {
        return showShimmer ? VIEW_TYPE_SHIMMER : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return showShimmer ? 10 : personList.size();
    }

    public void setPersonList(List<Person> productList) {
        this.personList = productList;
        showShimmer = false;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SHIMMER) {
            ItemShimmerBinding binding = ItemShimmerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ShimmerViewHolder(binding);
        } else {
            ItemPersonBinding binding = ItemPersonBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new PersonViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PersonViewHolder) {
            PersonViewHolder personViewHolder = (PersonViewHolder) holder;
            Person person = personList.get(position);
            personViewHolder.bind(person);
        } else if (holder instanceof ShimmerViewHolder) {
            ShimmerViewHolder shimmerViewHolder = (ShimmerViewHolder) holder;
            shimmerViewHolder.shimmerLayout.startShimmer();
        }
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder {
        private final ItemPersonBinding binding;

        public PersonViewHolder(ItemPersonBinding binding) {
            super(binding.root);
            this.binding = binding;
        }

        private <T extends Activity> View.OnClickListener openActivity(Person person) {
            return view -> {
                Intent intent = new Intent(context, PersonFormActivity.class);
                intent.putExtra(PersonFormActivity.INTENT_PERSON, person);
                context.startActivity(intent);
            };
        }

        public void bind(Person person) {
            binding.setPerson(person);
            binding.editIcon.setOnClickListener(openActivity(person));
        }

    }

}
