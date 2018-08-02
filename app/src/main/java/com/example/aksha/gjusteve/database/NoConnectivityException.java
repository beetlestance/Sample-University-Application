package com.example.aksha.gjusteve.database;

import java.io.IOException;

class NoConnectivityException extends IOException {

    @Override
    public String getMessage() {
        return "No connectivity exception";
    }

}