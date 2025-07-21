// This file is required by karma.conf.js and loads recursively all the .spec and framework files

import 'zone.js/testing'; // Included with Angular CLI.
import { getTestBed } from '@angular/core/testing';
import {
  BrowserDynamicTestingModule,
  platformBrowserDynamicTesting
} from '@angular/platform-browser-dynamic/testing';

declare const require: {
  context(path: string, deep?: boolean, filter?: RegExp): {
    keys(): string[];
    <T>(id: string): T;
  };
};

// First, initialize the Angular testing environment.
getTestBed().initTestEnvironment(
  BrowserDynamicTestingModule,
  platformBrowserDynamicTesting()
);

// Try to use require.context for test loading (Angular CLI/Webpack 5+)
try {
  const context = (require as any).context('./', true, /\.spec\.ts$/);
  context.keys().forEach(context);
} catch (e) {
  console.warn('Dynamic test loading via require.context is not available in this environment. No tests will be run.');
}
