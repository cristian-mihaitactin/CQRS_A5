/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GatewayTestModule } from '../../../test.module';
import { OrdinComponent } from '../../../../../../main/webapp/app/entities/ordin/ordin.component';
import { OrdinService } from '../../../../../../main/webapp/app/entities/ordin/ordin.service';
import { Ordin } from '../../../../../../main/webapp/app/entities/ordin/ordin.model';

describe('Component Tests', () => {

    describe('Ordin Management Component', () => {
        let comp: OrdinComponent;
        let fixture: ComponentFixture<OrdinComponent>;
        let service: OrdinService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [GatewayTestModule],
                declarations: [OrdinComponent],
                providers: [
                    OrdinService
                ]
            })
            .overrideTemplate(OrdinComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(OrdinComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OrdinService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Ordin(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.ordins[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
