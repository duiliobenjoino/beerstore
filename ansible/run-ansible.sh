#!/bin/bash

echo "Starting ansible ..."

ANSIBLE_HOST_KEY_CHECKING=false ansible-playbook -i ../terraform/hosts --private-key ../terraform/keys/beerstore_key beerstore_playbook.yml -v