#!/usr/bin/env bash

docker-compose -f zk.yml up -d && docker-compose -f broker-1.yml up -d && docker-compose -f broker-2.yml up -d