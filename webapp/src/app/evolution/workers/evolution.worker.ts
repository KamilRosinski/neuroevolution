/// <reference lib="webworker" />

import {Generation} from '../model/generation';
import {interval} from 'rxjs';
import {Settings} from '../model/settings';

addEventListener('message', (event: MessageEvent<Settings>) => {
  interval(200).subscribe((count: number) => {
    postMessage({
      id: count,
      score: Math.floor(Math.random() * 100)
    } as Generation);
  });
});
