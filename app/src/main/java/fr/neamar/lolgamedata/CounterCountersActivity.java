package fr.neamar.lolgamedata;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import fr.neamar.lolgamedata.adapter.CounterCountersAdapter;
import fr.neamar.lolgamedata.adapter.CounterCountersNoDataAdapter;
import fr.neamar.lolgamedata.pojo.Counter;

public class CounterCountersActivity extends SnackBarActivity {
    private Counter counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_counters);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        counter = (Counter) getIntent().getSerializableExtra("counter");

        CounterCountersAdapter adapter = new CounterCountersAdapter(counter);
        recyclerView.setAdapter(adapter);

        CounterCountersNoDataAdapter noDataAdapter = new CounterCountersNoDataAdapter(counter);
        RecyclerView noDataRecyclerView = (RecyclerView) findViewById(R.id.noData);
        noDataRecyclerView.setAdapter(noDataAdapter);

        if (counter.noData.size() == 0) {
            findViewById(R.id.noDataHolder).setVisibility(View.INVISIBLE);
        }

        getSupportActionBar().setTitle(String.format(getString(R.string.counter_counters_activity_title), counter.champion.name));

        if (counter.counters.size() == 0) {
            displaySnack(String.format(getString(R.string.no_counters), counter.champion.name));
        }

        Tracker.trackViewChampionCounters(this, counter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.action_champion_details) {
            Intent detailIntent = new Intent(this, ChampionActivity.class);
            detailIntent.putExtra("championName", counter.champion.name);
            detailIntent.putExtra("championId", counter.champion.id);
            detailIntent.putExtra("from", "counter");

            startActivity(detailIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_counter_counters, menu);

        return true;
    }
}
