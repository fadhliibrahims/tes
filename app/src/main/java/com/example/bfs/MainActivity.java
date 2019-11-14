package com.example.bfs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    ArrayList<Integer> numbers;
    TextView square;
    Button hitung;
    TextView hasil;
    boolean isBlack;
    int[][] maze;
    int m = 5;
    int n = 5;
    class pair
    {
        int first, second;
        pair(int first, int second)
        {
            this.first = first;
            this.second = second;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isBlack = false;
        maze = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                maze[i][j] = 1;
            }
        }
        numbers = new ArrayList<>();
        for (int i = 0; i < m*n; i++) {
            numbers.add(i);
        }
        gridView = findViewById(R.id.labirin);
        gridView.setAdapter(new ArrayAdapter<>(this, R.layout.list_item, numbers));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                square = view.findViewById(android.R.id.text1);
                if (!isBlack) {
                    square.setBackgroundColor(getResources().getColor(R.color.black, null));
                    maze[position/m][position%n] = 0;
                    isBlack = true;
                } else {
                    square.setBackgroundColor(getResources().getColor(R.color.white, null));
                    maze[position/m][position%n] = 1;
                    isBlack = false;
                }
            }
        });
        hasil = findViewById(R.id.hasil);
        hitung = findViewById(R.id.btnHitung);
        hitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasil.setText(String.valueOf(counting(maze)));
            }
        });
    }

    int counting(int[][] matrix)
    {
        Queue<pair> q = new LinkedList<>();

        // Insert the starting point i.e.
        // (0, 0) in the queue
        q.add(new pair(0, 0));

        // To store the count of possible paths
        int count = 0;

        while (!q.isEmpty())
        {
            pair p = q.peek();
            q.remove();

            // Increment the count of paths since
            // it is the destination
            assert p != null;
            if (p.first == n - 1 && p.second == m - 1)
                count++;

            // If moving to the next row is a valid move
            if (p.first + 1 < n &&
                    matrix[p.first + 1][p.second] == 1)
            {
                q.add(new pair(p.first + 1, p.second));
            }

            // If moving to the next column is a valid move
            if (p.second + 1 < m &&
                    matrix[p.first][p.second + 1] == 1)
            {
                q.add(new pair(p.first, p.second + 1));
            }
        }
        return count;
    }
}
