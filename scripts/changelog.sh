#!/bin/bash

# Copyright (C) 2018 Timothy "ZeevoX" Langer
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

if [ ! -z "$TRAVIS_TAG" ]
then
    TRAVIS_COMMIT_RANGE="$(git describe --abbrev=0 --tags ${TRAVIS_TAG}^)..$TRAVIS_TAG"
fi

GIT_COMMIT_LOG="$(git log --format='%s (by %an)' ${TRAVIS_COMMIT_RANGE})"

echo " <b>Changelog for test build #${TRAVIS_BUILD_NUMBER}</b>${NEWLINE}"

printf '%s\n' "$GIT_COMMIT_LOG" | while IFS= read -r line
do
    echo "- ${line}"
done
